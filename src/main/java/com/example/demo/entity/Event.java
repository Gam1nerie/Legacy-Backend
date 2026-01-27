package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "evenements") // On force le nom de la table
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private String description;
    private String jeu; // ex: "Warhammer", "Echecs", etc.
    private int nbrmax;
    private LocalDateTime dateEvenement; // Indispensable pour ton tri et suppression
    private double prix;

    @ManyToMany
    @JoinTable(
      name = "event_participants",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getJeu() { return jeu; }
    public void setJeu(String jeu) { this.jeu = jeu; }
    public int getNbrmax() { return nbrmax; }
    public void setNbrmax(int nbrmax) { this.nbrmax = nbrmax; }
    public LocalDateTime getDateEvenement() { return dateEvenement; }
    public void setDateEvenement(LocalDateTime dateEvenement) { this.dateEvenement = dateEvenement; }
    public List<User> getParticipants() { return participants; }
    public void setParticipants(List<User> participants) { this.participants = participants; }
    // Dans ton projet Backend


public double getPrix() { return prix; }
public void setPrix(double prix) { this.prix = prix; }

@ElementCollection
@CollectionTable(name = "event_guests", joinColumns = @JoinColumn(name = "event_id"))
@Column(name = "guest_name")
private List<String> guests = new ArrayList<>();


public List<String> getGuests() { return guests; }
public void setGuests(List<String> guests) { this.guests = guests; }
}