drop procedure if exists remove_money(text, text, text, int);

create or replace procedure remove_money(arg_card_id bigint, arg_money int)
as $$
declare
    card record;
begin
    select * from BankCard where id = arg_card_id into card;

    if card is null then
        raise exception 'Нет такой банковской карты %', arg_card_id;
    end if;

    update BankCard set money = money - arg_money where id = arg_card_id;
end;
$$ language plpgsql;
