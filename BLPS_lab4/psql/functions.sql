drop procedure if exists "remove_money"(text, text, text, int);

create or replace procedure "remove_money"(arg_card_id bigint, arg_money int)
as $$
declare
    card record;
begin
    select * from "BankCard" where id = arg_card_id into card;

    if card is null then
        raise exception 'Нет такой банковской карты %', arg_card_id;
    end if;

    update "BankCard" set money = money - arg_money where id = arg_card_id;
end;
$$ language plpgsql;


drop procedure if exists "attach_user_card"(bigint, text, text, text);

create or replace procedure "attach_user_card"(arg_user_id bigint, arg_serial_number text, arg_validity_date text, arg_cvv text)
as $$
declare
    card record;
begin
    select * from "BankCard" where serial_number = arg_serial_number into card;

    if card is null then
        raise exception 'Нет такой банковской карты %', arg_serial_number;
    end if;

    if card.validity_date != arg_validity_date then
        raise exception 'Ожидаемый срок действия (%) не совпал с переданным (%)', card.validity_date, arg_validity_date;
    end if;

    if card.cvv != arg_cvv then
        raise exception 'Ожидаемый CVV (%) не совпал с переданным (%)', card.cvv, arg_cvv;
    end if;

    if (select count(*) from "User" where id = arg_user_id) != 1 then
        raise exception 'Нет такого пользователя %', arg_user_id;
    end if;

    update "BankCard" set user_id = arg_user_id
    where serial_number = arg_serial_number;
end;
$$ language plpgsql;
