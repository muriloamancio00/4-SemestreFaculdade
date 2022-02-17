DELIMITER $$
CREATE PROCEDURE total_vendas_cliente(IN idP INT, OUT total REAL)
BEGIN

    DECLARE totalVendas REAL DEFAULT 0;
    DECLARE valorVenda REAL DEFAULT 0;
    DECLARE terminou INTEGER DEFAULT 0;

    DECLARE vendas_cursor CURSOR FOR
SELECT valorTotal FROM vendas where idPessoa=idP;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET terminou = 1;
OPEN vendas_cursor;

get_vendas:
    LOOP
        FETCH vendas_cursor into valorVenda;
        IF terminou = 1 THEN
            LEAVE get_vendas;
END IF;
        SET totalVendas = totalVendas + valorVenda;

END LOOP get_vendas;

CLOSE vendas_cursor;

SET total = totalVendas;

END $$
DELIMITER ;