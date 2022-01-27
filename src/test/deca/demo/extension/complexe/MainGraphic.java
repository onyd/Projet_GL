public class MainGraphic
{
    public static void main(String[] args)
    {
        Draw d = new Draw();
        Rectangle rect = new Rectangle();
        Point topLeft = new Point();
        Point bottomRight = new Point();

        //set the size of the screen
        d.setSize(5, 5);

        //create the rectangle
        topLeft.Point(0, 4);
        bottomRight.Point(4, 0);
        rect.Rectangle(topLeft, bottomRight);
        d.draw(rect);
    }
}