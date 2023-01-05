package com.alice.cursomc.domain;

import com.alice.cursomc.domain.enums.EstadoPagamento;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pagamento implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;
    @Id
    private Integer id;
    private EstadoPagamento estadoPagamento;

    @OneToOne
    @JoinColumn(name="pedido_id")
    @MapsId
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(Integer id, EstadoPagamento estadoPagamento, Pedido pedido) {
        this.id = id;
        this.estadoPagamento = estadoPagamento;
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento pagamento)) return false;

        return id.equals(pagamento.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
