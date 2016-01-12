iptables -F INPUT
iptables -F OUTPUT
iptables -A INPUT  --source      172.16.1.0/24 -j DROP
iptables -A OUTPUT --destination 172.16.1.0/24 -j DROP
iptables -I INPUT  --source      172.16.1.0/24 --protocol tcp --source-port      51000 -j ACCEPT
iptables -I OUTPUT --destination 172.16.1.0/24 --protocol tcp --destination-port 51000 -j ACCEPT
