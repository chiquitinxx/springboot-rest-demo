package framework.model

/**
 * Created by jorge on 01/03/14.
 */
class Framework {
    BigInteger id
    String name
    String url
    String urlImage

    boolean hasImage() {
        urlImage && ['.GIF', '.PNG', '.JPG'].any { urlImage.toUpperCase().endsWith(it)}
    }

    boolean equals(Object other) {
        other instanceof Framework && other.name == this.name
    }

    boolean isGithub()  {
        url.contains('github.com')
    }
}
