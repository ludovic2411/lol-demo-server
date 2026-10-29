package org.example.loldemoserver.champions.dtos.out;

import java.util.Map;

public record ChampionListRecordDto(String type, String format, String version, Map<String, Object> data) {
}
