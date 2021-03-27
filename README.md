<h1 align="center">Trading Telegram Bot</h1>

<div align="center">
Just sale and purchase yourself (27.03.2021 v.1)

[![API Version](https://img.shields.io/badge/telegrambots-5.0.1-blue)](https://github.com/rubenlagus/TelegramBots)
[![Build Status](https://img.shields.io/badge/spring-2.4.3-brightgreen)](https://spring.io)
[![Build Status](https://img.shields.io/badge/jdk-15-orange)](https://www.oracle.com/java/technologies/javase/15-relnote-issues.html)

[![https://telegram.me/node_telegram_bot_api](https://img.shields.io/badge/Telegram-Bot-blue)](https://t.me/TradingHackBot)
</div>

## Table of Contents

- [Introduction](#introduction)
- [Technology stack](#technology-stack)
- [Configuration](#configuration)
- [How to use](#how-to-use)

## Introduction

This is a Spring Boot Telegram Bot for managing sales and purchases
without any difficulties. Just check this out!

This bot is based on Long Polling.

The Bot can:

- Save CSV data about quotations from [finam.ru](https://www.finam.ru/)
- Show all available tickers
- Trade
- Keep and show balance 
- Export processed CSV with trades

## Technology stack

- Java 15
- TelegramBots API
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Redis
- Maven

## Configuration

To set up a configuration you have to add values to Environment variables.

### Configuration Values

- `TELEGRAM_BOT_USERNAME` - Create a bot via [BotFather](https://t.me/botfather)
- `TELEGRAM_BOT_TOKEN` - Get token by contacting to [BotFather](https://t.me/botfather)
- `SPRING_DATASOURCE_URL` - Postgres database url
- `SPRING_DATASOURCE_USERNAME` - Postgres database username
- `SPRING_DATASOURCE_PASSWORD` - Postgres database password
- `SPRING_REDIS_SERVER_HOST` - Redis host address
- `SPRING_REDIS_SERVER_PORT` - Redis server port

## How to use

Here are all commands you can use to communicate with our trading bot.

### /start
Simply to start bot.

### /help
Display everything you need to communicate with our bot.

### /upload
Save CSV from [finam.ru](https://www.finam.ru/) and send it to our bot.

### /tools
Show all available (loaded) tickers that you have uploaded via `/upload` command.

### /trade
Set trading time range and tickers to start trade.

Template: `/trade <from> <to> <ticker-1>...<ticker-n>`

Example: `/trade 11:10:30 13:50:20 AMD-RM MSNG`

### /balance
Get balance of every traded ticker.

### /download
Display all trades or save a CSV file.

Templates:

`/download` -> all trades with ids.

`/download #<trade id>` -> export CSV file.

## Copyright

Copyright © 2021 by [LookFor Inc](https://github.com/LookFor-Inc)
