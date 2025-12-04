package com.exemplo.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "aeroporto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aeroporto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aeroporto")
    Integer idAeroporto;

    @NotNull
    @Size(max = 150)
    @Column(name = "nome_aeroporto", length = 80)
    String nomeAeroporto;

    @Size(max = 3)
    @Column(name = "codigo_iata", length = 3)
    @NotNull
    String codigoIATA;

    @Size(max = 100)
    @Column(name = "cidade", length = 50)
    @NotNull
    String cidade;

    @Size(max = 2)
    @Column(name = "codigo_pais_iso", length = 2)
    @NotNull
    String codigoISO;

    @Column(name = "latitude", precision = 10, scale = 6)
    @NotNull
    Double latitude;

    @Column(name = "longitude", precision = 10, scale = 6)
    @NotNull
    Double longitude;

    @Column(name = "altitude", precision = 10, scale = 2)
    @NotNull
    Double altitude;
}
