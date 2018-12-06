--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6
-- Dumped by pg_dump version 10.6

-- Started on 2018-12-06 15:17:12 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE bank_manager;
--
-- TOC entry 3187 (class 1262 OID 16998)
-- Name: bank_manager; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE bank_manager WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'ru_RU.UTF-8' LC_CTYPE = 'ru_RU.UTF-8';


ALTER DATABASE bank_manager OWNER TO postgres;

\connect bank_manager

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 13281)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 201 (class 1259 OID 17025)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 16999)
-- Name: invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoice (
    id bigint NOT NULL,
    cash money,
    number character varying(255),
    user_id bigint
);


ALTER TABLE public.invoice OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 17004)
-- Name: motion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.motion (
    sender_invoice_id bigint NOT NULL,
    recipient_invoice_id bigint NOT NULL
);


ALTER TABLE public.motion OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 17009)
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction (
    id bigint NOT NULL,
    cash money,
    date date,
    "time" time without time zone
);


ALTER TABLE public.transaction OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 17014)
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    roles character varying(255)
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 17017)
-- Name: usr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usr (
    id bigint NOT NULL,
    active boolean NOT NULL,
    address character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    second_name character varying(255),
    username character varying(255)
);


ALTER TABLE public.usr OWNER TO postgres;

--
-- TOC entry 3176 (class 0 OID 16999)
-- Dependencies: 196
-- Data for Name: invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.invoice (id, cash, number, user_id) FROM stdin;
\.


--
-- TOC entry 3177 (class 0 OID 17004)
-- Dependencies: 197
-- Data for Name: motion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.motion (sender_invoice_id, recipient_invoice_id) FROM stdin;
\.


--
-- TOC entry 3178 (class 0 OID 17009)
-- Dependencies: 198
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction (id, cash, date, "time") FROM stdin;
\.


--
-- TOC entry 3179 (class 0 OID 17014)
-- Dependencies: 199
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_role (user_id, roles) FROM stdin;
1	ADMIN
1	USER
1	CLIENT
2	CLIENT
\.


--
-- TOC entry 3180 (class 0 OID 17017)
-- Dependencies: 200
-- Data for Name: usr; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usr (id, active, address, first_name, last_name, password, second_name, username) FROM stdin;
1	t	RO, Bataysk, Komsomolskaya 115b/23	Aleksandr	Semenov	$2a$08$fNFLK3XPBtTzU2pd9WIMAOy/mPgZwKTIPf4/667iWwLlZvK5UR21O	Aleksandrovich	folk-lore
2	t	Rostov-on-Don, Nansena st., 64/15	Pavel	Rikov	$2a$08$sN8b.awKM19jkrFD5PLJeOnJgC/DAqWZKTwW0duqcbFwERu3lH0Xu	Aleksandrovich	poll-nill
\.


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 201
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 2, true);


--
-- TOC entry 3044 (class 2606 OID 17003)
-- Name: invoice invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_pkey PRIMARY KEY (id);


--
-- TOC entry 3046 (class 2606 OID 17008)
-- Name: motion motion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.motion
    ADD CONSTRAINT motion_pkey PRIMARY KEY (sender_invoice_id, recipient_invoice_id);


--
-- TOC entry 3048 (class 2606 OID 17013)
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- TOC entry 3050 (class 2606 OID 17024)
-- Name: usr usr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr
    ADD CONSTRAINT usr_pkey PRIMARY KEY (id);


--
-- TOC entry 3052 (class 2606 OID 17032)
-- Name: motion fk29aslp8j4tm1ktbwp6ld0jivw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.motion
    ADD CONSTRAINT fk29aslp8j4tm1ktbwp6ld0jivw FOREIGN KEY (recipient_invoice_id) REFERENCES public.invoice(id);


--
-- TOC entry 3053 (class 2606 OID 17037)
-- Name: motion fk9ikvm0pnij6qt4bcy5cq3ovxw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.motion
    ADD CONSTRAINT fk9ikvm0pnij6qt4bcy5cq3ovxw FOREIGN KEY (sender_invoice_id) REFERENCES public.transaction(id);


--
-- TOC entry 3054 (class 2606 OID 17042)
-- Name: user_role fkfpm8swft53ulq2hl11yplpr5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkfpm8swft53ulq2hl11yplpr5 FOREIGN KEY (user_id) REFERENCES public.usr(id);


--
-- TOC entry 3051 (class 2606 OID 17027)
-- Name: invoice fkgxq7hway3bg1fl0durvvk825a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT fkgxq7hway3bg1fl0durvvk825a FOREIGN KEY (user_id) REFERENCES public.usr(id);


--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-12-06 15:17:12 MSK

--
-- PostgreSQL database dump complete
--

