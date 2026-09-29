package org.example.loldemoserver.champions.out;

import java.util.Map;

public record ChampionListRecordDto(String type, String format, String version, Map<String, Object> data) {
}
