package rscvanilla.xp.domain.entities;

import lombok.*;
import rscvanilla.xp.domain.models.SyncroResultStatus;
import rscvanilla.xp.persistance.listeners.AuditableEntityListener;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@EntityListeners(AuditableEntityListener.class)
public class SyncroResult extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column
    @Getter @Setter
    private SyncroResultStatus status;

    @Column
    @Getter @Setter
    private int tryCount;

    @Builder
    public SyncroResult(SyncroResultStatus status, int tryCount) {
        this.status = status;
        this.tryCount = tryCount;
    }

    public String getState() {
        if (createdAt == null && updatedAt == null) {
            return "INIT_MISSING";
        } else if (createdAt == null || updatedAt == null) {
            throw new IllegalStateException("CreatedAt or UpdatedAt can't be exclusively null.");
        } else if (createdAt.equals(updatedAt)) {
            if (status.equals(SyncroResultStatus.OK)) {
                return "INIT_OK";
            } else {
                return "INIT_NOK";
            }
        } else if (createdAt.compareTo(updatedAt) < 0) {
            if (status.equals(SyncroResultStatus.OK)) {
                if (tryCount == 1) {
                    return "AFTER_INIT_OK_TO_OK";
                } else if (tryCount > 1) {
                    return "AFTER_INIT_NOK_TO_OK";
                } else {
                    throw new IllegalStateException("TryCount can't be 0.");
                }
            } else { // This block is executed only if syncro fails from initial
                if (tryCount < 2) {
                    throw new IllegalStateException("TryCount can't be < 2>");
                } else {
                    return "AFTER_INIT_NOK_TO_NOK";
                }
            }
        } else {
            throw new IllegalStateException("CreatedAt can't be > UpdatedAt");
        }
    }

    public static SyncroResult createMissingResult() {
        return new SyncroResult();
    }
}
