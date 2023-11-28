package programmingtechnology.creatormemes.logics;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

public class Meme {
    private final ImageView imageView;
    private final Text text;
    private final List<Ellipse> ellipses;
    public static class Builder
    {
        private Random random = new Random();
        private List<String> strings = List.of(
                "Это было не просто смело\n - это было очень смело!",
                "Ну это капец, \nсколько можно!",
                "Я ошибся,я могу\n один раз ошибиться!",
                "Я это не понимаю,\n мне это не интересно!",
                "Это, конечно,\n печально!");
        private List<Color> colors = List.of(Color.RED, Color.LIME, Color.BLUE, Color.GREEN);

        //Обязательные параметры
        private final ImageView imageView;
        //Необязательные параметры
        private Text text = null;
        private List<Ellipse> ellipses = null;
        public Builder(Iterator iter, ImageView imageView){
            this.imageView = imageView;
            if(iter.hasNext()){
                var image = (Image) iter.next();
                this.imageView.setImage(image);
            }
        }
        public Builder Text(Text text){
            text.setText(strings.get(random.nextInt(strings.size())));
            text.setFill(colors.get(random.nextInt(colors.size())));
            this.text = text;
            return this;
        }
        public Builder Ellipses(List<Ellipse> ellipses) {
            this.ellipses = ellipses;
            return  this;
        }
        public Meme build(){
            return  new Meme(this);
        }
    }
    private Meme(Builder builder){
        imageView = builder.imageView;
        text = builder.text;
        ellipses = builder.ellipses;
    }
    public void setMeme(Pane pane){
        pane.getChildren().clear();
        pane.getChildren().add(imageView);
        if(ellipses != null)
            pane.getChildren().addAll(ellipses);
        if(text != null)
            pane.getChildren().add(text);
    }
}
