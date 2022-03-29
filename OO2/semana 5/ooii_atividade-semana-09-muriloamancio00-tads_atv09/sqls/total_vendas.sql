DELIMITER $$
CREATE PROCEDURE total_vendas(OUT total REAL)
BEGIN

    DECLARE totalvendas REAL DEFAULT 0;
    DECLARE valorvenda REAL DEFAULT 0;
    DECLARE terminou INTEGER DEFAULT 0;

    DECLARE vendas_cursor CURSOR FOR
SELECT valorTotal FROM vendas;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET terminou = 1;
OPEN vendas_cursor;

get_vendas:
    LOOP
        FETCH vendas_cursor into valorvenda;
        IF terminou = 1 THEN
            LEAVE get_vendas;
END IF;
        SET totalvendas = totalvendas + valorvenda;

END LOOP get_vendas;

CLOSE vendas_cursor;

SET total = totalvendas;

END $$
DELIMITER ;