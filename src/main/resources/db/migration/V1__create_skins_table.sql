CREATE TABLE IF NOT EXISTS champions_skins (
                                 username    VARCHAR(255) NOT NULL,
                                 champion_id VARCHAR(255) NOT NULL,
                                 skin_id     VARCHAR(255) NOT NULL,
                                 CONSTRAINT pk_champions_skins PRIMARY KEY (username, champion_id, skin_id)
);