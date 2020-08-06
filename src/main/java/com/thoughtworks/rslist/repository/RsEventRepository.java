package com.thoughtworks.rslist.repository;
import com.thoughtworks.rslist.entity.RsEventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface  RsEventRepository extends CrudRepository<RsEventEntity,Integer>{
@Override
    List<RsEventEntity> findAll();
}
