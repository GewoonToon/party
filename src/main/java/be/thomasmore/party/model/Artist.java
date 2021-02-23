package be.thomasmore.party.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Artist {
    @Id
    private int id;
    private String artist_Name, link_More_Info, genre, bio, portfolio;

    public Artist(){}

    public Artist(int id, String artistName, String linkMoreInfo, String genre, String bio, String portfolio) {
        this.id = id;
        this.artist_Name = artistName;
        this.link_More_Info = linkMoreInfo;
        this.genre = genre;
        this.bio = bio;
        this.portfolio = portfolio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtistName() {
        return artist_Name;
    }

    public void setArtistName(String artistName) {
        this.artist_Name = artistName;
    }

    public String getLinkMoreInfo() {
        return link_More_Info;
    }

    public void setLinkMoreInfo(String linkMoreInfo) {
        this.link_More_Info = linkMoreInfo;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }
}
