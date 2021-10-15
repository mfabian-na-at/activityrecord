package mx.com.naat.activityrecord.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mx.com.naat.activityrecord.infrastructure.entity.ActivityRecord;

@Repository
public interface ActivityRecordRepository extends PagingAndSortingRepository<ActivityRecord, UUID> {

	List<ActivityRecord> findByIdAuthorOrderByDateDescIdProjectDesc(UUID idAuthor, Pageable page);

	Integer countByIdAuthor(UUID idAuthor);

	List<ActivityRecord> findDistinctByIdAuthorAndDateBeforeOrderByDateDesc(UUID idAuthor, LocalDate date);

	ActivityRecord findByIdAuthorAndIdProjectAndIdActivity(UUID idAuthor, UUID idProject, UUID idActivity);

	boolean existsByIdAuthorAndIdProjectAndIdActivity(UUID idAuthor, UUID idProject, UUID idActivity);

	boolean existsByIdAuthorAndIdProjectAndIdActivityAndDate(UUID idAuthor, UUID idProject, UUID idActivity,
			LocalDate date);

	boolean existsByIdAndIdAuthor(UUID idActivityRecord, UUID idAuthor);

	List<ActivityRecord> findByIdAuthorAndDate(UUID idAuthor, LocalDate date);

	ActivityRecord findByIdAuthorAndIdProjectAndIdActivityAndDate(UUID idAuthor, UUID idProject, UUID idActivity,
			LocalDate date);

}
