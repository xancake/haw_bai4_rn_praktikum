Lab23:
sudo /sbin/route add -net 192.168.18.0/24 gw 192.168.17.1
sudo /sbin/route add -A inet6 fd32:6de0:1f69:18::/64 gw fd32:6de0:1f69:17::2

Lab31:
sudo /sbin/route add -net 192.168.17.0/24 gw 192.168.18.1
sudo /sbin/route add -A inet6 fd32:6de0:1f69:17::/64 gw fd32:6de0:1f69:18::2
