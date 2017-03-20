package luiz.br.com.movies;

import java.io.Serializable;

/**
 * Created by Luiz on 14/03/2017.
 */

public class Movies implements Serializable {
    private Long id;
    private String name;
    private String rating;
    private String duration;

    public Movies(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
