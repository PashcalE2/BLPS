create or replace procedure remove_money(arg_card_serial text, arg_card_validity text, arg_card_cvv text, arg_money int)
as $$
declare
    card record;
begin
    select * from BankCard where serial_number = arg_card_serial into card;

    if card is null then
        raise exception 'Нет такой банковской карты %', arg_card_serial;
    end if;

    if card.validity_date != arg_card_validity then
        raise exception 'Срок действия не совпал (ожидаемый: %, полученный: %)', card.validity_date, arg_card_validity;
    end if;

    if card.cvv != arg_card_cvv then
        raise exception 'Код CVV не совпал (ожидаемый: %, полученный: %)', card.cvv, arg_card_cvv;
    end if;

    update BankCard set money = money - arg_money where serial_number = arg_card_serial;
end;
$$ language plpgsql;
