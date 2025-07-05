package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categoria categoria);
    List<Serie> TotalTemporadasLessThanEqual(Integer temporadas);
    List<Serie> findByEvaluacion(double evaluacion);

//    @Query(value = "SELECT * FROM public.series where public.series.total_temporadas <= 6 and public.series.evaluacion >= 7.5",nativeQuery = true)
//    List<Serie> seriesPorTemporadayEvaluacion();

    @Query("SELECT s FROM Serie s where s.totalTemporadas <= :totalTemporadas and s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadayEvaluacion(int totalTemporadas, double evaluacion);

    @Query("select e from Serie s join s.episodios e where e.titulo ilike %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("select e from Serie s join s.episodios e where s = :serie order by e.evaluacion desc limit 5")
    List<Episodio> top5Episodios(Serie serie);
}
