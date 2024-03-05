package com.ltw.repository.masterData;

import com.ltw.model.MasterData;
import com.ltw.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MasterDataRepository extends JpaRepository<MasterData, Integer>, JpaSpecificationExecutor<MasterData> {
}
