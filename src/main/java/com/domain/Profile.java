package com.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;

import com.domain.enumeration.Musictype;

/**
 * The Profil entity.\n@author joel jorle
 */
@ApiModel(description = "The Profil entity.\n@author joel jorle")
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le_genre
     */
    @ApiModelProperty(value = "Le_genre")
    @Column(name = "sexe")
    private String sexe;

    /**
     * age
     */
    @ApiModelProperty(value = "age")
    @Column(name = "age")
    private Integer age;

    /**
     * Lieu_de_residence
     */
    @ApiModelProperty(value = "Lieu_de_residence")
    @Column(name = "resident_state")
    private String residentState;

    /**
     * numero_de_telephone
     */
    @ApiModelProperty(value = "numero_de_telephone")
    @Column(name = "phone")
    private Integer phone;

    /**
     * nom_artiste
     */
    @ApiModelProperty(value = "nom_artiste")
    @Column(name = "artist_name")
    private String artistName;

    /**
     * categorie_de_musique
     */
    @ApiModelProperty(value = "categorie_de_musique")
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private Musictype categorie;

    /**
     * Autre_categorie_de_musique
     */
    @ApiModelProperty(value = "Autre_categorie_de_musique")
    @Column(name = "other_music_type")
    private String otherMusicType;

    /**
     * avez_vous_participer_a_un_evenement
     */
    @ApiModelProperty(value = "avez_vous_participer_a_un_evenement")
    @Column(name = "event_participation")
    private Boolean eventParticipation;

    /**
     * nom_de_levenement
     */
    @ApiModelProperty(value = "nom_de_levenement")
    @Column(name = "event_participation_name")
    private String eventParticipationName;

    /**
     * temps_dexperience
     */
    @ApiModelProperty(value = "temps_dexperience")
    @Column(name = "experience_time")
    private Integer experienceTime;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSexe() {
        return sexe;
    }

    public Profile sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Integer getAge() {
        return age;
    }

    public Profile age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getResidentState() {
        return residentState;
    }

    public Profile residentState(String residentState) {
        this.residentState = residentState;
        return this;
    }

    public void setResidentState(String residentState) {
        this.residentState = residentState;
    }

    public Integer getPhone() {
        return phone;
    }

    public Profile phone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getArtistName() {
        return artistName;
    }

    public Profile artistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Musictype getCategorie() {
        return categorie;
    }

    public Profile categorie(Musictype categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Musictype categorie) {
        this.categorie = categorie;
    }

    public String getOtherMusicType() {
        return otherMusicType;
    }

    public Profile otherMusicType(String otherMusicType) {
        this.otherMusicType = otherMusicType;
        return this;
    }

    public void setOtherMusicType(String otherMusicType) {
        this.otherMusicType = otherMusicType;
    }

    public Boolean isEventParticipation() {
        return eventParticipation;
    }

    public Profile eventParticipation(Boolean eventParticipation) {
        this.eventParticipation = eventParticipation;
        return this;
    }

    public void setEventParticipation(Boolean eventParticipation) {
        this.eventParticipation = eventParticipation;
    }

    public String getEventParticipationName() {
        return eventParticipationName;
    }

    public Profile eventParticipationName(String eventParticipationName) {
        this.eventParticipationName = eventParticipationName;
        return this;
    }

    public void setEventParticipationName(String eventParticipationName) {
        this.eventParticipationName = eventParticipationName;
    }

    public Integer getExperienceTime() {
        return experienceTime;
    }

    public Profile experienceTime(Integer experienceTime) {
        this.experienceTime = experienceTime;
        return this;
    }

    public void setExperienceTime(Integer experienceTime) {
        this.experienceTime = experienceTime;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", sexe='" + getSexe() + "'" +
            ", age=" + getAge() +
            ", residentState='" + getResidentState() + "'" +
            ", phone=" + getPhone() +
            ", artistName='" + getArtistName() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", otherMusicType='" + getOtherMusicType() + "'" +
            ", eventParticipation='" + isEventParticipation() + "'" +
            ", eventParticipationName='" + getEventParticipationName() + "'" +
            ", experienceTime=" + getExperienceTime() +
            "}";
    }
}
