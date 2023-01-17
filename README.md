# email-sender-api

Now the application only works with ionos server mail.

You have to run the aplication with two environment variables:
- FROM=<email from ionos used to send the email>
- PASSWORD=<password>

Example of request:
´´´
{
    "to": "",
    "cc": "",
    "bcc": "",
    "subject": "",
    "text": ""
}
´´´