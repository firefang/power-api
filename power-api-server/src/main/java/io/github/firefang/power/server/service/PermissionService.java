package io.github.firefang.power.server.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.firefang.power.permission.serialize.IPermissionService;
import io.github.firefang.power.permission.serialize.PermissionDO;
import io.github.firefang.power.server.entity.domain.PermDO;
import io.github.firefang.power.server.mapper.IPermissionMapper;

/**
 * @author xinufo
 *
 */
@Service
public class PermissionService implements IPermissionService {
    private IPermissionMapper permissionMapper;

    public PermissionService(IPermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public Map<String, List<PermDO>> list() {
        return permissionMapper.findAll().stream().collect(Collectors.groupingBy(PermDO::getGroup));
    }

    @Override
    public void upsertBatch(List<PermissionDO> entities) {
        Map<String, PermDO> map = entities.stream().collect(Collectors.toMap(PermissionDO::getName, p -> {
            PermDO entity = new PermDO();
            entity.setName(p.getName());
            entity.setGroup(p.getGroup());
            return entity;
        }));
        permissionMapper.deleteNameNotIn(map.keySet());

        List<PermDO> toInsert = new LinkedList<>();
        List<PermDO> toUpdate = new LinkedList<>();

        Map<String, PermDO> all = permissionMapper.findAll().stream()
                .collect(Collectors.toMap(PermissionDO::getName, p -> {
                    PermDO entity = new PermDO();
                    entity.setId(p.getId());
                    entity.setName(p.getName());
                    entity.setGroup(p.getGroup());
                    return entity;
                }));

        for (Map.Entry<String, PermDO> e : map.entrySet()) {
            String name = e.getKey();
            PermDO dataToUpdate = e.getValue();
            PermDO dataInDB = all.get(name);
            if (dataInDB == null) {
                toInsert.add(dataToUpdate);
            } else if (!dataToUpdate.getGroup().equals(dataInDB.getGroup())) {
                dataToUpdate.setId(dataInDB.getId());
                toUpdate.add(dataToUpdate);
            }
        }
        if (!toInsert.isEmpty()) {
            permissionMapper.addBatch(toInsert);
        }
        if (!toUpdate.isEmpty()) {
            permissionMapper.updateBatch(toUpdate);
        }
    }

}
