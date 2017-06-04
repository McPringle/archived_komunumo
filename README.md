*CoMa*
======

[![Build Status](https://travis-ci.org/jugswitzerland/coma.svg?branch=master)](https://travis-ci.org/jugswitzerland/coma) [![Stories in Ready](https://badge.waffle.io/jugswitzerland/coma.png?label=ready&title=ready)](http://waffle.io/jugswitzerland/coma) [![gitmoji](https://img.shields.io/badge/gitmoji-%20😜%20😍-FFDD67.svg)](https://gitmoji.carloscuesta.me)

**Open Source Community Manager**

*Copyright (C) 2017 Java User Group Switzerland*

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.

## Configuration

*CoMa* uses [MongoDB](https://wikipedia.org/wiki/MongoDB) to store it's data. Therefore you must provide the credentials for a running database instance through environment variables:

| Environment Variable | Description |
| -------------------- | ------------- |
| db_username          | The name of the user (login) for authorization. |
| db_password          | The password used to authorize the user. |
| db_host              | The host which runs the MongoDB instance |
| db_port              | The port which provides access to the MongoDB server. |
| db_name              | The name of the database to be used by *Moodini*. |

## Throughput

[![Throughput Graph](https://graphs.waffle.io/jugswitzerland/coma/throughput.svg)](https://waffle.io/jugswitzerland/coma/metrics/throughput)
