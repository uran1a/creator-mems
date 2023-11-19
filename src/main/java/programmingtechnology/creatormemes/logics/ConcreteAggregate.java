package programmingtechnology.creatormemes.logics;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConcreteAggregate implements Aggregate{
    private List<File> images;
    public ConcreteAggregate(File filetopic) {
        this.images = findAllImages(filetopic);
    }

    private List<File> findAllImages(File dir){
        List<String> supportedImageExtensions = Arrays.asList("jpg", "png");

        List<File> acceptedImages = new ArrayList<>();
        for (File file : dir.listFiles()) {
            String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (supportedImageExtensions.stream().anyMatch(fileExtension::equalsIgnoreCase)) {
                acceptedImages.add(file);
            }
        }
        return acceptedImages;
    }
    private class ImageIterator implements Iterator {
        private int current = 0;
        private int setCurrent(int value) {
            if(value < 0)
                current = images.size() - 1;
            else
                current = value % images.size();
            return current;
        }
        private Image getImage(int i){
            return new Image(images.get(i).toURI().toString(), 100.0, 100.0, true, true);
        }
        @Override
        public boolean hasNext() {
            return current < images.size() && images.get(current) != null;
        }

        @Override
        public Object next() { return getImage(setCurrent(current+1)); }

        @Override
        public Object preview() {
            return getImage(setCurrent(current-1));
        }
    }

    @Override
    public Iterator getIterator() {

        return new ImageIterator();
    }
}
