# Notification Manager

Listens for events via Apache Kafka

# How it works
* Applications sending Kafka messages to the consumer add the dto module (notification-manager-dto) as
a dependency which contains the request objects

* Current receives e-mail and audit events

E-mail Notifications:
* Leverages Twilio SendGrid for e-mail templates and as the transactional e-mail service
* Based on what type of e-mail is to be sent, the template id refers to a specific template
* Template properties are populated and the e-mail is sent

Audit Notifications:
* Audit request is turned into an Audit object and stored into the DB