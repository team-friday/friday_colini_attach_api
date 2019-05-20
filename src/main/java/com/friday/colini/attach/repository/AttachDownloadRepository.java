package com.friday.colini.attach.repository;

import com.friday.colini.attach.entity.AttachDownload;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface AttachDownloadRepository extends CrudRepository<AttachDownload, Long> {
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Modifying
    @Query("UPDATE AttachDownload at SET at.count = (at.count + 1) WHERE at.id = :id")
    void increaseDownloadCount(@Param("id") final long id);
}
