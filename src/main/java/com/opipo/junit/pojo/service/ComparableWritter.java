package com.opipo.junit.pojo.service;

import com.opipo.junit.pojo.model.ClassLoaded;
import org.springframework.stereotype.Service;

@Service
public class ComparableWritter {

    public String writeMethods(ClassLoaded clazz){
        String nombreClase = clazz.getClassName().substring(0, 1).toLowerCase() + clazz.getClassName().substring(1);
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder hashCode = new StringBuilder();
        StringBuilder equals = new StringBuilder();
        StringBuilder compareTo = new StringBuilder();
        hashCode.append("@Override").append(lineSeparator);
        hashCode.append("public int hashCode() {").append(lineSeparator);
        hashCode.append("final HashCodeBuilder hcb = new HashCodeBuilder();").append(lineSeparator);
        equals.append("@Override").append(lineSeparator);
        equals.append("public boolean equals(Object object) {").append(lineSeparator);
        equals.append("if (!(object instanceof ").append(clazz.getClassName()).append(")) {").append(lineSeparator);
        equals.append("return false;").append(lineSeparator);
        equals.append("}").append(lineSeparator);
        equals.append("final ").append(clazz.getClassName()).append(" other = ( ").append(clazz.getClassName())
                .append(" ) object;").append(lineSeparator);
        equals.append("final EqualsBuilder eqb = new EqualsBuilder();").append(lineSeparator);
        compareTo.append("@Override").append(lineSeparator);
        compareTo.append("public int compareTo(").append(clazz.getClassName()).append(" other) {").append(lineSeparator);
        compareTo.append("final CompareToBuilder ctb = new CompareToBuilder();").append(lineSeparator);
        for (String attribute : clazz.getAttributes()) {
            String[] atributeSplit = attribute.split(" ");
            String nombreAtributo = atributeSplit[1].substring(0, 1).toUpperCase() + atributeSplit[1].substring(1);
            StringBuilder getter = new StringBuilder();
            getter.append(atributeSplit[0].equalsIgnoreCase("boolean")?"is":"get").append(nombreAtributo).append("()");
            hashCode.append("hcb.append(").append(getter.toString()).append(");").append(lineSeparator);
            equals.append("eqb.append(this.").append(getter.toString()).append(", other.").append(getter.toString())
                    .append(");").append(lineSeparator);
            compareTo.append("ctb.append(this.").append(getter.toString()).append(", other.").append(getter.toString())
                    .append(");").append(lineSeparator);
        }
        hashCode.append("return hcb.toHashCode();").append(lineSeparator);
        hashCode.append("}").append(lineSeparator);
        equals.append("return eqb.isEquals();").append(lineSeparator);
        equals.append("}").append(lineSeparator);
        compareTo.append("return ctb.toComparison();").append(lineSeparator);
        compareTo.append("}").append(lineSeparator);

        StringBuilder result = new StringBuilder();
        result.append(hashCode);
        result.append(lineSeparator);
        result.append(equals);
        result.append(lineSeparator);
        result.append(compareTo);

        return result.toString();
    }
}
