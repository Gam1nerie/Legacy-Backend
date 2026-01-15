package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String postalCode;
    private String role = "USER"; 


    private boolean magic;
    private boolean pokemon;
    private boolean lorcana;
    private boolean altered;
    private boolean riftbound;
    private boolean onePiece;

@Column(name = "warhammer40k") 
private boolean w40k;

@Column(name = "warhammer_aos")
private boolean waos;
    private boolean boardgames;
    private boolean rpg;

    private boolean isAdmin = false;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; 
    public boolean isMagic() { return magic; }
    public void setMagic(boolean magic) { this.magic = magic; }
    public boolean isPokemon() { return pokemon; }
    public void setPokemon(boolean pokemon) { this.pokemon = pokemon; }
    public boolean isLorcana() { return lorcana; }
    public void setLorcana(boolean lorcana) { this.lorcana = lorcana; }
    public boolean isAltered() { return altered; }
    public void setAltered(boolean altered) { this.altered = altered; }
    public boolean isRiftbound() { return riftbound; }
    public void setRiftbound(boolean riftbound) { this.riftbound = riftbound; }
    public boolean isOnePiece() { return onePiece; }
    public void setOnePiece(boolean onePiece) { this.onePiece = onePiece; }
    public boolean isW40k() { return w40k; }
    public void setW40k(boolean w40k) { this.w40k = w40k; }
    public boolean isWaos() { return waos; }
    public void setWaos(boolean waos) { this.waos = waos; }
    public boolean isBoardgames() { return boardgames; }
    public void setBoardgames(boolean boardgames) { this.boardgames = boardgames; }
    public boolean isRpg() { return rpg; }
    public void setRpg(boolean rpg) { this.rpg = rpg; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
}
