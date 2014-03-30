package framework.model

/**
 * Created by jorge on 01/03/14.
 */
class Framework {
    String name
    String url
    String urlImage

    boolean hasImage() {
        urlImage && ['.GIF', '.PNG', '.JPG'].any { urlImage.toUpperCase().endsWith(it)}
    }
}
