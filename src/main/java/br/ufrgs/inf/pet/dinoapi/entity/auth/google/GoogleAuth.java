package br.ufrgs.inf.pet.dinoapi.entity.auth.google;

import br.ufrgs.inf.pet.dinoapi.constants.GoogleAuthConstants;
import br.ufrgs.inf.pet.dinoapi.entity.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "google_auth")
public class GoogleAuth {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "google_id", length = GoogleAuthConstants.GOOGLE_ID_MAX, unique = true, nullable = false)
    private String googleId;

    @Column(name = "refresh_token", length = GoogleAuthConstants.REFRESH_TOKEN_MAX, unique = true)
    private String refreshToken;

    @Column(name = "api_access_token", length = GoogleAuthConstants.ACCESS_TOKEN_MAX, unique = true)
    private String accessToken;

    @Column(name = "api_access_token_expires_date", length = GoogleAuthConstants.ACCESS_TOKEN_MAX, unique = true)
    private LocalDateTime accessTokenExpiresDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne(mappedBy = "googleAuth")
    private GoogleAccessToken googleAccessToken;

    @OneToMany(mappedBy = "googleAuth", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GoogleScope> googleScopes;

    public GoogleAuth() { }

    public GoogleAuth(String googleId, String refreshToken, User user) {
        this.googleId = googleId;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getAccessTokenExpiresDate() {
        return accessTokenExpiresDate;
    }

    public void setAccessTokenExpiresDate(LocalDateTime accessTokenExpiresDate) {
        this.accessTokenExpiresDate = accessTokenExpiresDate;
    }
}
