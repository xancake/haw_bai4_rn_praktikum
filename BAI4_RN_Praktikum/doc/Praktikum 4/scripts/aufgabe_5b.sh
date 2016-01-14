sudo /usr/sbin/iptables -I OUTPUT -p tcp --dport 80 -j REJECT --reject-with tcp-reset
sudo /usr/sbin/iptables -I OUTPUT -d www.dmi.dk -p tcp --dport 80 -j ACCEPT
