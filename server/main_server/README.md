# S-Leaf API

## Endpoints
This API consists of:
1. ```[IP/domain_name]/upload```
    To upload an image and get the information of the predicted species.

1. ```[IP/domain_name]/random```
    To get 3 random plants information.

1. ```[IP/domain_name]/all```
    To get information about all plants species stored in the server.


## RESPONSE FORMAT
Response of 1 record will look like this:
```json
{
    "class_name": "Alpinia Galanga (Lengkuas)",
    "name": "Lengkuas",
    "desc": "Lengkuas, laos atau kelawas merupakan jenis tumbuhan umbi-umbian yang bisa hidup di daerah dataran tinggi maupun dataran rendah. Umumnya masyarakat memanfaatkannya sebagai campuran bumbu masak dan pengobatan tradisional.",
    "scientific_name": "Alpinia galanga",
    "img_urls": [
        "https://prasetio23.files.wordpress.com/2015/01/download-3.jpg",
        "https://foto.kontan.co.id/taUhIcxCd3YrdcwZsCFbmDGa1sw=/smart/2020/06/11/553414440p.jpg"
    ],
    "score": "58%"
}
```

Response of >1 records (/random, /all) will look like:
```json
[
    record_1,
    record_2,
    .
    .
    .
]
```
