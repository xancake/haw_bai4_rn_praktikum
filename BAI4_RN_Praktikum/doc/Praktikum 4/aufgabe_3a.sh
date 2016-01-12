iptables -F INPUT
iptables -F OUTPUT
iptables -A INPUT  --source      172.16.1.0/24 -j DROP
iptables -A OUTPUT --destination 172.16.1.0/24 -j DROP
