PGDMP     )                    v            bank_manager    9.6.11    11.1 (Debian 11.1-1.pgdg90+1)     d           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            e           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            f           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            g           1262    19776    bank_manager    DATABASE     �   CREATE DATABASE "bank_manager" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'ru_RU.UTF-8' LC_CTYPE = 'ru_RU.UTF-8';
    DROP DATABASE "bank_manager";
             postgres    false            �            1259    19799    hibernate_sequence    SEQUENCE        CREATE SEQUENCE "public"."hibernate_sequence"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE "public"."hibernate_sequence";
       public       jaxx    false            �            1259    19777    invoice    TABLE     �   CREATE TABLE "public"."invoice" (
    "id" bigint NOT NULL,
    "cash" numeric(14,2) DEFAULT 0.00,
    "number" character varying(255),
    "user_id" bigint
);
    DROP TABLE "public"."invoice";
       public         jaxx    false            �            1259    19783    transaction    TABLE       CREATE TABLE "public"."transaction" (
    "id" bigint NOT NULL,
    "cash" numeric(14,2),
    "recipient_cash" numeric(14,2),
    "sender_cash" numeric(14,2),
    "tstz" timestamp without time zone,
    "invoice_recipient" bigint,
    "invoice_sender" bigint
);
 #   DROP TABLE "public"."transaction";
       public         jaxx    false            �            1259    19788 	   user_role    TABLE     i   CREATE TABLE "public"."user_role" (
    "user_id" bigint NOT NULL,
    "roles" character varying(255)
);
 !   DROP TABLE "public"."user_role";
       public         jaxx    false            �            1259    19791    usr    TABLE     H  CREATE TABLE "public"."usr" (
    "id" bigint NOT NULL,
    "active" boolean NOT NULL,
    "address" character varying(255),
    "first_name" character varying(255),
    "last_name" character varying(255),
    "password" character varying(255),
    "second_name" character varying(255),
    "username" character varying(255)
);
    DROP TABLE "public"."usr";
       public         jaxx    false            �           2606    19782    invoice invoice_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY "public"."invoice"
    ADD CONSTRAINT "invoice_pkey" PRIMARY KEY ("id");
 D   ALTER TABLE ONLY "public"."invoice" DROP CONSTRAINT "invoice_pkey";
       public         jaxx    false    185            �           2606    19787    transaction transaction_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY "public"."transaction"
    ADD CONSTRAINT "transaction_pkey" PRIMARY KEY ("id");
 L   ALTER TABLE ONLY "public"."transaction" DROP CONSTRAINT "transaction_pkey";
       public         jaxx    false    186            �           2606    19798    usr usr_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY "public"."usr"
    ADD CONSTRAINT "usr_pkey" PRIMARY KEY ("id");
 <   ALTER TABLE ONLY "public"."usr" DROP CONSTRAINT "usr_pkey";
       public         jaxx    false    188            �           2606    19806 '   transaction fk2u6u6kguev9t0n21593o203ux    FK CONSTRAINT     �   ALTER TABLE ONLY "public"."transaction"
    ADD CONSTRAINT "fk2u6u6kguev9t0n21593o203ux" FOREIGN KEY ("invoice_recipient") REFERENCES "public"."invoice"("id");
 W   ALTER TABLE ONLY "public"."transaction" DROP CONSTRAINT "fk2u6u6kguev9t0n21593o203ux";
       public       jaxx    false    2020    186    185            �           2606    19811 '   transaction fk859uval8jwl2yhywxe1lexwjv    FK CONSTRAINT     �   ALTER TABLE ONLY "public"."transaction"
    ADD CONSTRAINT "fk859uval8jwl2yhywxe1lexwjv" FOREIGN KEY ("invoice_sender") REFERENCES "public"."invoice"("id");
 W   ALTER TABLE ONLY "public"."transaction" DROP CONSTRAINT "fk859uval8jwl2yhywxe1lexwjv";
       public       jaxx    false    185    186    2020            �           2606    19816 $   user_role fkfpm8swft53ulq2hl11yplpr5    FK CONSTRAINT     �   ALTER TABLE ONLY "public"."user_role"
    ADD CONSTRAINT "fkfpm8swft53ulq2hl11yplpr5" FOREIGN KEY ("user_id") REFERENCES "public"."usr"("id");
 T   ALTER TABLE ONLY "public"."user_role" DROP CONSTRAINT "fkfpm8swft53ulq2hl11yplpr5";
       public       jaxx    false    187    2024    188            �           2606    19801 #   invoice fkgxq7hway3bg1fl0durvvk825a    FK CONSTRAINT     �   ALTER TABLE ONLY "public"."invoice"
    ADD CONSTRAINT "fkgxq7hway3bg1fl0durvvk825a" FOREIGN KEY ("user_id") REFERENCES "public"."usr"("id");
 S   ALTER TABLE ONLY "public"."invoice" DROP CONSTRAINT "fkgxq7hway3bg1fl0durvvk825a";
       public       jaxx    false    188    185    2024           