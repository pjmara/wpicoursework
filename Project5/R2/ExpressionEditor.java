import javafx.application.Application;
import java.util.*;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExpressionEditor extends Application {
	/**
	 * Current focus of the editor. By default it is null
	 */
	private static Expression focus;
	/**
	 * The starting X coordinate of the localX in method setFocus
	 */
	private static double focus_x_off = 0;
	/**
	 * A deep copy of the current focus. Empty when there is no drag and drop
	 */
	private static Expression focus_copy;

	/**
	 * Size of the GUI
	 */
	private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 250;
	/**
	 * Height of the button
	 */
	private static final double BUTTON_Y_OFF = 28;
	/**
	 * Y Coordinate of the root node
	 */
	private static final double Y_OFF = WINDOW_HEIGHT / 2;
	/**
	 * X Coordinate of the root node
	 */
	private static final double X_OFF = WINDOW_WIDTH / 4;
	/**
	 * A magic number to work with the accumulator
	 */
	private static final int MAGIC_NUMBER = 5;

	public static void main (String[] args) {
		launch(args);
	}

	/**
	 * Mouse event handler for the entire pane that constitutes the ExpressionEditor
	 */
	private static class MouseEventHandler implements EventHandler<MouseEvent> {
		private Pane pane;
		private CompoundExpression rootExpression;
		private double lastX, lastY;

		MouseEventHandler (Pane pane_, CompoundExpression rootExpression_) {
			pane = pane_;
			rootExpression = rootExpression_;
		}

		public void handle (MouseEvent event) {
			final double sceneX = event.getSceneX();
			final double sceneY = event.getSceneY();

			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				
				if (focus != null && focus_copy == null) { // Generate a copy of the focus
					double localX = sceneX - X_OFF - focus_x_off;
					double localY = sceneY - BUTTON_Y_OFF - Y_OFF;
					
					if (focus != null && focus.contains(localX, localY)) {
						generateCopy();
						setColor(focus.getNode(), Expression.GHOST_COLOR);
					}
				}
				
			} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double localX = sceneX - X_OFF - focus_x_off;
				double localY = sceneY - BUTTON_Y_OFF - Y_OFF;

				if (focus_copy == null) { // Generate a copy of the focus
					if (focus != null && focus.contains(localX, localY)) {
						generateCopy();
						setColor(focus.getNode(), Expression.GHOST_COLOR);
					}
				} else { // Update the copy's position, rootExpression, and rootPane
					Node node = focus_copy.getNode();
					node.setTranslateX(sceneX - lastX);
					node.setTranslateY(sceneY - lastY);
					checkAndUpdatePositions(sceneX);
				}
				
			} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
				lastX = X_OFF + focus_x_off;
				lastY = Y_OFF + BUTTON_Y_OFF;
				if (focus_copy != null && focus != null) { // Finalize the expression after the dragging
					pane.getChildren().remove(focus_copy.getNode());
					setColor(focus.getNode(), Expression.GHOST_BUSTER);
					if (focus_copy.getNode().getTranslateX() <= 1) {
						setFocus(sceneX, sceneY);
						focus_copy = null;
					} else {
						focus_copy = null;
						System.out.println(rootExpression.convertToString(0));
					}
				} else { // Set a new focus
					setFocus(sceneX, sceneY);
					lastX = X_OFF + focus_x_off;
					lastY = Y_OFF + BUTTON_Y_OFF;
				}
			}
		}

		/**
		 * check and update the positions of the node, and the expression
		 * @param sceneX the X coordinate of the mouse
		 */
		private void checkAndUpdatePositions(double sceneX) {
			Expression parentExp = focus.getParent();
			Pane parentNode = (Pane) parentExp.getNode();
			double xCood; // The X coordinate relative to the origin of the parentExp
			if (parentExp.equals(rootExpression)) {
				xCood = sceneX - parentNode.getLayoutX();
			} else {
				xCood = sceneX - X_OFF - focus_x_off + focus.getNode().getLayoutX();
			}
			HashMap<Double, Expression> positions = new HashMap<Double, Expression>();
			
			// Generate the possible positions of the focus, and store them into a hashmap
			for (int i = 0; i < parentExp.getChildren().size(); i ++) {
				Expression parentCopy = parentExp.deepCopy();
				List<Expression> children = parentCopy.getChildren();
				Expression focusCopy = parentCopy.getChildren().get(focus.getParent().getChildren().indexOf(focus));
				HashMap<Expression, Double> nodeWidths = new HashMap<Expression, Double>();
				for(int j = 0; j < children.size(); j ++) {
					nodeWidths.put(children.get(j), 
							parentExp.getChildren().get(j).getNode().getLayoutBounds().getWidth());
				}
				// yes I know this is a mess
				double symbolWidth = ((Pane) parentExp.getNode()).getChildren().get(1).getLayoutBounds().getWidth();
				
				children.remove(focusCopy);
				children.add(i, focusCopy);
				
				double position = 0;
				for (int j = 0; j < i; j ++) {
					position += symbolWidth;
					position += nodeWidths.get(children.get(j));
				}
				positions.put(position, parentExp.getChildren().get(i));
			
			}
			
			double closestPosition = computeClosestDistance (positions.keySet(), xCood);
			Expression closestExp = positions.get(closestPosition);
			if (!closestExp.equals(focus)) {
				swapExp (parentExp, parentNode, closestExp);
			}
		}
		
		
		/**
		 * Swap the position of the focus and the closest node in both pane and expression
		 * @param parentExp the parent Expression of the current focus
		 * @param parentNode the parent node of the current focus node
		 * @param closestExp the expression closest to the mouse's position
		 */
		private void swapExp(Expression parentExp, Pane parentNode, Expression closestExp) {
			Node closestNode = closestExp.getNode();
			List<Expression> expChildren = parentExp.getChildren();
			List<Node> nodeChildren = parentNode.getChildren();
			
			int idx = expChildren.indexOf(closestExp);
			Expression dummyExp = expChildren.set(expChildren.indexOf(focus), closestExp);
			expChildren.set(idx, dummyExp);
			
			idx = nodeChildren.indexOf(closestNode);
			nodeChildren.set(idx, new Pane());
			Node dummyNode = nodeChildren.set(nodeChildren.indexOf(focus.getNode()), closestNode);
			nodeChildren.set(idx, dummyNode);
		}
		
		/**
		 * compute the closest distance from the mouse to any one of the node that is sibling to the focus
		 * @param positionValues relative to the origin of their parent node
		 * @param xCood the mouse's X coordinate relative to the origin of the parent node
		 * @return the closest distance from the mouse to any one of the node that is sibling to the focus
		 */
		private double computeClosestDistance(Set<Double> positionValues, double xCood) {
			double closestPosition = focus.getNode().getLayoutX();
			double closestDistance = Double.MAX_VALUE;
			
			for (Double position: positionValues) {
				double newDistance = Math.abs(xCood - position);
				if (newDistance < closestDistance) {
					closestPosition = position;
					closestDistance = newDistance;
				}
			}
			
			return closestPosition;
		}
		
		/**
		 * generate a deep copy of the focus
		 */
		private void generateCopy() {
			focus_copy = focus.deepCopy();
			Node nodeCopy = focus_copy.getNode();
			nodeCopy.setLayoutX(X_OFF + focus_x_off);
			nodeCopy.setLayoutY(Y_OFF);
			setColor(focus.getNode(), Expression.GHOST_COLOR);
			pane.getChildren().add(nodeCopy);
		}

		/**
		 * Set the color of the labels within the given node to the given color
		 * @param node the node whose color needs to be changed
		 * @param color the chosen color
		 */
		private void setColor(Node node, Color color) {
			if (node.getClass().getName().equals("javafx.scene.layout.HBox")) {
				for (Node child: ((Pane) node).getChildren()) {
					setColor(child, color);
				}
			} else {
				((Label) node).setTextFill(color);
			}
		}

		/**
		 * set focus according to the current focus and the mouse position
		 * @param sceneX the sceneX of the mouse
		 * @param sceneY the sceneY of the mouse
		 */
		private void setFocus(double sceneX, double sceneY) {
			int accumulator = 0;
			final Expression exp;
			if (focus == null) {
				exp = rootExpression;
			} else {
				exp = focus;
			}			

			for (Expression child: exp.getChildren()) {
				double localX = sceneX - X_OFF - child.getNode().getLayoutX() - focus_x_off;
				double localY = sceneY - BUTTON_Y_OFF - Y_OFF;
				if (child.contains(localX, localY)) {
					((Pane) exp.getNode()).setBorder(Expression.NO_BORDER);
					focus = child;
					((Pane) child.getNode()).setBorder(Expression.RED_BORDER);
					accumulator = child.getChildren().size() + MAGIC_NUMBER;
					focus_x_off += child.getNode().getLayoutX();
					break;
				}
				accumulator ++;
			}

			if (focus != null) {
				if (accumulator == 0 || accumulator == focus.getChildren().size()) {
					((Pane) focus.getNode()).setBorder(Expression.NO_BORDER);
					focus = null;
					focus_x_off = 0;
				}
			}
		}
	}

	/**
	 * Initial expression shown in the textbox
	 */
	private static final String EXAMPLE_EXPRESSION = "2*x+3*y+4*z+(7+6*z)";

	/**
	 * Parser used for parsing expressions.
	 */
	private final ExpressionParser expressionParser = new SimpleExpressionParser();

	@Override
	public void start (Stage primaryStage) {
		primaryStage.setTitle("Expression Editor");

		// Add the textbox and Parser button
		final Pane queryPane = new HBox();
		final TextField textField = new TextField(EXAMPLE_EXPRESSION);
		final Button button = new Button("Parse");
		queryPane.getChildren().add(textField);

		final Pane expressionPane = new Pane();

		// Add the callback to handle when the Parse button is pressed	
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle (MouseEvent e) {
				// Try to parse the expression
				try {
					// Success! Add the expression's Node to the expressionPane
					focus = null;
					final Expression expression = expressionParser.parse(textField.getText(), true);
					System.out.println(expression.convertToString(0));
					expressionPane.getChildren().clear();
					expressionPane.getChildren().add(expression.getNode());
					expression.getNode().setLayoutX(WINDOW_WIDTH/4);
					expression.getNode().setLayoutY(WINDOW_HEIGHT/2);

					// If the parsed expression is a CompoundExpression, then register some callbacks
					if (expression instanceof CompoundExpression) {
						((Pane) expression.getNode()).setBorder(Expression.NO_BORDER);
						final MouseEventHandler eventHandler = new MouseEventHandler(expressionPane, (CompoundExpression) expression);
						expressionPane.setOnMousePressed(eventHandler);
						expressionPane.setOnMouseDragged(eventHandler);
						expressionPane.setOnMouseReleased(eventHandler);
					}
				} catch (ExpressionParseException epe) {
					// If we can't parse the expression, then mark it in red
					textField.setStyle("-fx-text-fill: red");
				}
			}
		});
		queryPane.getChildren().add(button);

		// Reset the color to black whenever the user presses a key
		textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

		final BorderPane root = new BorderPane();
		root.setTop(queryPane);
		root.setCenter(expressionPane);

		primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
		primaryStage.show();
	}
}
