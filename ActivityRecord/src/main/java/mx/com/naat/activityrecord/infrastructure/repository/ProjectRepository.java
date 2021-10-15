package mx.com.naat.activityrecord.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import mx.com.naat.activityrecord.infrastructure.entity.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, UUID> {
}
