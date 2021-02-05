package com.example.Notes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Document(value = "Accept")
public class Accept {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank(message = "Item-Name is mandatory.")
    @NotNull
    private String name;

    @NotBlank(message = "Item-MarketName is mandatory.")
    @NotNull
    @JsonProperty(value = "market_name")
    private String marketName;

    @NotBlank
    @NotNull
    private String quality;

    private String effect;
    private String ware;
    private String paint;
    private boolean craftable;
    private boolean hasKillstreakActive;
    private boolean hasKillstreakSheen;
    private String killstreakSheen;
    private boolean hasKillstreakEffect;
    private String killstreakEffect;

    @JsonProperty(value = "BuyAt")
    @NotNull
    @Digits(integer = 10, fraction = 0)
    private int buyAt;
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @JsonProperty(value = "SellAt")
    private int sellAt;
    private boolean isFestivized;

    public boolean updateWithItem(Accept item) {
        this.marketName = item.getMarketName();
        this.quality = item.getQuality();
        this.effect = item.getEffect();
        this.ware = item.getWare();
        this.paint = item.getPaint();
        this.craftable = item.isCraftable();
        this.hasKillstreakActive = item.isHasKillstreakActive();
        this.killstreakSheen = item.getKillstreakSheen();
        this.hasKillstreakEffect = item.isHasKillstreakEffect();
        this.buyAt = item.getBuyAt();
        this.sellAt = item.getSellAt();

        return true;
    }

    public boolean checkIsEqual(Accept item) {
        if (
                Objects.equals(this.name, item.getName()) &&
                        Objects.equals(this.marketName, item.getMarketName()) &&
                        Objects.equals(this.quality, item.getQuality()) &&
                        Objects.equals(this.effect, item.getEffect()) &&
                        Objects.equals(this.ware, item.getWare()) &&
                        Objects.equals(this.paint, item.getPaint()) &&
                        Objects.equals(this.craftable, item.isCraftable()) &&
                        Objects.equals(this.hasKillstreakActive, item.isHasKillstreakActive()) &&
                        Objects.equals(this.killstreakSheen, item.getKillstreakSheen()) &&
                        Objects.equals(this.hasKillstreakEffect, item.isHasKillstreakEffect()) &&
                        Objects.equals(this.buyAt, item.getBuyAt()) &&
                        Objects.equals(this.sellAt, item.getSellAt())
        ) {
            return true;
        }

        return false;
    }
}
