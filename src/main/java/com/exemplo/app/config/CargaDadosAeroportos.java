package com.exemplo.app.config;

import com.exemplo.app.model.Aeroporto;
import com.exemplo.app.repository.AeroportoRepository;
import com.exemplo.app.service.AeroportoService;
import com.exemplo.app.utils.CountryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;


@Configuration
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class CargaDadosAeroportos implements CommandLineRunner {

    private final AeroportoRepository aeroportoRepository;

    @Override
    public void run(String... args) throws Exception{
        if (aeroportoRepository.count()>0){
            log.info("Banco de dados já contém registros. Carga ignorada.");
            return;
        }

        log.info("Iniciando carga de aeroportos via CSV (ISO-8859-1)...");

        ClassPathResource resource = new ClassPathResource("airports.csv");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.ISO_8859_1)
        )){

            String line;
            int salvos = 0;
            int ignorados = 0;

            while ((line = reader.readLine()) != null){
                try {
                    String[] dados = line.split(";");

                    if (dados.length < 9){
                        ignorados++;
                        continue;
                    }

                    String nome = dados[1];
                    String cidade = dados[2];
                    String pais = dados[3];
                    String iata = dados[4];
                    String latStr = dados[6];
                    String lonStr = dados[7];
                    String altStr = dados[8];

                    if (iata==null || iata.trim().length() != 3 || iata.equals("\\N")){
                        ignorados++;
                        continue;
                    }

                    String iso = CountryUtils.getIsoCode(pais);
                    if (iso == null) iso = "XX";

                    Aeroporto aeroporto = new Aeroporto();

                    if (nome.length() > 150) nome = nome.substring(0,150);
                    if (cidade.length() > 100) cidade = cidade.substring(0,100);

                    aeroporto.setNomeAeroporto(nome);
                    aeroporto.setCidade(cidade);
                    aeroporto.setCodigoISO(iso);
                    aeroporto.setCodigoIATA(iata);

                    // Convertendo String para BigDecimal
                    aeroporto.setLatitude(Double.parseDouble(latStr));
                    aeroporto.setLongitude(Double.parseDouble(lonStr));

                    Double altitude = aeroporto.converterPesParaMetros(Double.parseDouble(altStr));
                    aeroporto.setAltitude(altitude);

                    aeroportoRepository.save(aeroporto);
                    salvos++;
                } catch (Exception e){
                    log.error("Erro ao processar linha: {}", line, e);
                    ignorados++;
                }
            }

            log.info("Carga finalizada. Salvos: {}. Ignorados: {}", salvos, ignorados);
        }
    }

}
