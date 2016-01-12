iptables -F INPUT
iptables -A INPUT --source 141.22.192.100 -j ACCEPT
iptables -A INPUT --source filercpt.informatik.haw-hamburg.de -j ACCEPT
iptables -A INPUT --source localhost -j ACCEPT

iptables -F OUTPUT
iptables -A OUTPUT --destination 141.22.192.100 -j ACCEPT
iptables -A OUTPUT --destination filercpt.informatik.haw-hamburg.de -j ACCEPT
iptables -A OUTPUT --destination localhost -j ACCEPT

sudo /sbin/rcSuSEfirewall2 restart