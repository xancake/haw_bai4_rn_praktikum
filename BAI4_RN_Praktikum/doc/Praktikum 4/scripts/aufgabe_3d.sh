sudo /usr/sbin/iptables -I INPUT --source 172.16.1.0/24 --protocol icmp --icmp-type echo-request -j REJECT
