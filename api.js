const express = require('express')
const app = express()
const port = 5000

app.get('/api/v1/record', (req, res) => {
    console.log(req.params())
    res.json({"message": "ok"}).status(200)
})

app.listen(port, () => {
    console.log(`Api listening on port ${port}`)
})