package cm.xenonbyte.gestitre.infrastructure.stock;

import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMoveLine;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface StockMoveJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "companyName.text.value", target = "companyName")
    @Mapping(source = "isinCode.text.value", target = "isinCode")
    @Mapping(expression = "java(StockNatureJpa.valueOf(stockMove.getNature().name()))", target = "natureJpa")
    @Mapping(expression = "java(StockMoveTypeJpa.valueOf(stockMove.getType().name()))", target = "typeJpa")
    @Mapping(expression = "java(StockMoveStateJpa.valueOf(stockMove.getState().name()))", target = "stateJpa")
    @Mapping(source = "quantityCredit.value", target = "quantityCredit")
    @Mapping(source = "quantityDebit.value", target = "quantityDebit")
    @Mapping(source = "filename.text.value", target = "filename")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMove.getObservation() == null || stockMove.getObservation().text().value().isEmpty()? null: stockMove.getObservation().text().value())", target = "observation")
    @Mapping(source = "reference.text.value", target = "reference")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.infrastructure.company.CompanyJpa.builder().id(stockMove.getCompanyId().getValue()).build())", target = "companyJpa")
    @Mapping(source = "moveLines", qualifiedByName = "moveLinesToMoveLineJpas", target = "moveLinesJpa")
    @Nonnull StockMoveJpa toStockMoveJpa(StockMove stockMove);

    @Named("moveLinesToMoveLineJpas")
    List<StockMoveLineJpa> moveLinesToMoveLineJpas(List<StockMoveLine> moveLines);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "accountNumber.text.value", target = "accountNumber")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "reference.text.value", target = "reference")
    @Mapping(expression = "java(StockMoveLineTypeJpa.valueOf(stockMoveLine.getType().name()))", target = "typeJpa")
    @Mapping(expression = "java(StockMoveLineStateJpa.valueOf(stockMoveLine.getState().name()))", target = "stateJpa")
    @Mapping(source = "quantity.value", target = "quantity")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMoveLine.getCity() == null || stockMoveLine.getCity().text().value().isEmpty()? null: stockMoveLine.getCity().text().value())", target = "city")
    @Mapping(expression = "java(stockMoveLine.getZipCode() == null || stockMoveLine.getZipCode().text().value().isEmpty()? null: stockMoveLine.getZipCode().text().value())", target = "zipCode")
    @Mapping(expression = "java(stockMoveLine.getAdministrator() == null || stockMoveLine.getAdministrator().text().value().isEmpty()? null: stockMoveLine.getAdministrator().text().value())", target = "administrator")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.infrastructure.shareholder.ShareHolderJpa.builder().id(stockMoveLine.getShareHolderId().getValue()).build())", target = "shareHolderJpa")
    @Mapping(expression = "java(StockMoveJpa.builder().id(stockMoveLine.getStockMoveId().getValue()).build())", target = "stockMoveJpa")
    @Nonnull StockMoveLineJpa moveLinesToMoveLineJpa(@Nonnull StockMoveLine stockMoveLine);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "companyName", target = "companyName.text.value")
    @Mapping(source = "isinCode", target = "isinCode.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockNature.valueOf(stockMoveJpa.getNatureJpa().name()))", target = "nature")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveType.valueOf(stockMoveJpa.getTypeJpa().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveState.valueOf(stockMoveJpa.getStateJpa().name()))", target = "state")
    @Mapping(source = "quantityCredit", target = "quantityCredit.value")
    @Mapping(source = "quantityDebit", target = "quantityDebit.value")
    @Mapping(source = "filename", target = "filename.text.value")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMoveJpa.getObservation() == null || stockMoveJpa.getObservation().isEmpty()? null: cm.xenonbyte.gestitre.domain.stock.vo.Observation.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(stockMoveJpa.getObservation())))", target = "observation")
    @Mapping(source = "reference", target = "reference.text.value")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.common.vo.CompanyId(stockMoveJpa.getCompanyJpa().getId()))", target = "companyId")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.common.vo.TenantId(stockMoveJpa.getTenantId()))", target = "tenantId")
    @Mapping(source = "moveLinesJpa", qualifiedByName = "moveLineJpaToMoveLines", target = "moveLines")
    @Nonnull StockMove toStockMove(StockMoveJpa stockMoveJpa);

    @Named("moveLineJpaToMoveLines")
    List<StockMoveLine> moveLineJpaToMoveLines(List<StockMoveLineJpa> moveLinesJpa);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "accountNumber", target = "accountNumber.text.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(source = "reference", target = "reference.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineType.valueOf(stockMoveLineJpa.getTypeJpa().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineState.valueOf(stockMoveLineJpa.getStateJpa().name()))", target = "state")
    @Mapping(source = "quantity", target = "quantity.value")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMoveLineJpa.getCity() == null || stockMoveLineJpa.getCity().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.City.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(stockMoveLineJpa.getCity())))", target = "city")
    @Mapping(expression = "java(stockMoveLineJpa.getZipCode() == null || stockMoveLineJpa.getZipCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.ZipCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(stockMoveLineJpa.getZipCode())))", target = "zipCode")
    @Mapping(expression = "java(stockMoveLineJpa.getAdministrator() == null || stockMoveLineJpa.getAdministrator().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.Administrator.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(stockMoveLineJpa.getAdministrator())))", target = "administrator")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId(stockMoveLineJpa.getShareHolderJpa().getId()))", target = "shareHolderId")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.stock.vo.StockMoveId(stockMoveLineJpa.getStockMoveJpa().getId()))", target = "stockMoveId")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.common.vo.TenantId(stockMoveLineJpa.getTenantId()))", target = "tenantId")
    @Nonnull StockMoveLine moveLineJpaToMoveLine(@Nonnull StockMoveLineJpa stockMoveLineJpa);

    void copyNewToOldStockMoveJpa(@Nonnull StockMoveJpa newStockMoveJpa, @Nonnull @MappingTarget StockMoveJpa oldStockMoveJpa);
}
