public class AdresseIp implements Comparable<AdresseIp> {
    short[] ip = new short[4];

    public AdresseIp(String ip) {
        String[] ipParts = ip.split("\\.");
        for (int i = 0; i < 4; i++) {
            this.ip[i] = (short) Integer.parseInt(ipParts[i]);
        }
    }

    @Override
    public int compareTo(AdresseIp o) {
        if (o == null) throw new NullPointerException();
        for (int i = 0; i < 4; i++) {
            if (ip[i] != o.ip[i]) {
                return o.ip[i] - ip[i];
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }

    @Override
    public boolean equals(Object obj) {
        // if the object is compared with itself then return true
        if (this == obj) return true;
        // if the object is null or not an instance of AdresseIp then return false
        if (!(obj instanceof AdresseIp adresseIp)) return false;
        // if the two objects have the same ip then return true
        return compareTo(adresseIp) == 0;
    }

    @Override
    public int hashCode() {
        return ip[0] + ip[1] + ip[2] + ip[3];
    }
}
