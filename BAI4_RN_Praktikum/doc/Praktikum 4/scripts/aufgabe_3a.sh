sudo /usr/sbin/iptables -I INPUT  --source      172.16.1.0/24 -j REJECT --reject-with tcp-reset
sudo /usr/sbin/iptables -I OUTPUT --destination 172.16.1.0/24 -j REJECT --reject-with tcp-reset
