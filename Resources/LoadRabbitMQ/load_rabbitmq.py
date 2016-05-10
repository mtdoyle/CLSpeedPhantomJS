# vim: tabstop=8 expandtab shiftwidth=4 softtabstop=4
import pika
import sys

if len(sys.argv) > 1:
    filename = sys.argv[1]
else:
    filename = 'addresses'

f = open(filename,'r')

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))

channel = connection.channel()

channel.queue_declare(queue='clspeed')

channel.queue_purge(queue='clspeed')

for item in f.readlines():
    channel.basic_publish(exchange='',
                        routing_key='clspeed',
                        body=item.strip(),)


connection.close()
