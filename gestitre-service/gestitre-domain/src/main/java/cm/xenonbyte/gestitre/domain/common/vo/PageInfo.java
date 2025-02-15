package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.exception.PageInitializationBadException;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 30/08/2024
 */
public final class PageInfo<T> {
    private Boolean first;
    private Boolean last;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private List<T> elements;

    public PageInfo() {
    }

    public PageInfo(Boolean first, Boolean last, Integer pageSize, Long totalElements,
                    Integer totalPages, List<T> elements) {
        this.first = first;
        this.last = last;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.elements = elements;
    }

    public static String computeOderBy(PageInfoField pageInfoField, PageInfoDirection pageInfoDirection) {
        return String.format("%s %s", pageInfoField.text().value(), pageInfoDirection.getValue());
    }

    public PageInfo<T> of(@Nonnull Integer page, @Nonnull Integer pageSize, @Nonnull List<T> items) {

        if(items.isEmpty()) {
            return new PageInfo<>(false, false, 0, 0L, 0, new ArrayList<>());
        }
        long total = items.size();
        int pages = (int) Math.ceil((double) total / pageSize);
        if(page < 0 || page - 1 > pages) {
            throw new PageInitializationBadException(new String[]{String.valueOf(page), String.valueOf(total)});
        }
        int startIndex = page * pageSize;
        int stopIndex = Math.min(startIndex + pageSize, (int)total);
        if( startIndex <= total ) {
            return new PageInfo<>(
                    page == 0,
                    page == pages - 1,
                    pageSize,
                    total,
                    pages,
                    items.subList(startIndex, stopIndex));
        }
        throw new PageInitializationBadException();
    }

    public static void validatePageParameters(
            PageInfoPage pageInfoPage,
            PageInfoSize pageInfoSize,
            PageInfoField pageInfoField,
            PageInfoDirection pageInfoDirection) {
        Assert.field("Page", pageInfoPage).notNull();
        Assert.field("Size", pageInfoSize).notNull();
        Assert.field("Field", pageInfoField).notNull();
        Assert.field("Direction", pageInfoDirection).notNull();
    }

    public Boolean getFirst() {
        return first;
    }

    public Boolean getLast() {
        return last;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<T> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageInfo<?> pageInfo = (PageInfo<?>) o;
        return Objects.equals(first, pageInfo.first) && Objects.equals(last, pageInfo.last) &&
                Objects.equals(pageSize, pageInfo.pageSize) && Objects.equals(totalElements, pageInfo.totalElements) &&
                Objects.equals(totalPages, pageInfo.totalPages) && Objects.equals(elements, pageInfo.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last, pageSize, totalElements, totalPages, elements);
    }
}
