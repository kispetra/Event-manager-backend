package com.hackathon.event.auth;
import javax.persistence.*;
@Entity
@Table(name="appuser")
public class appUser {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appuser_sequence")
        @SequenceGenerator(name = "appuser_sequence", allocationSize = 1)
        @Column(name = "id")
        private int userid;

        @Column(name="username")
        private String username;

        @Column(name="email", length = 255)
        private String email;

        @Column(name="password", length = 255)
        private String password;


        public appUser() {
        }

        public appUser(int userid, String username, String email, String password) {
            this.userid = userid;
            this.username = username;
            this.email = email;
            this.password = password;
        }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
