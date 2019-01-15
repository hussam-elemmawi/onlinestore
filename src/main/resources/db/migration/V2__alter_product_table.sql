ALTER TABLE public.product ADD code varchar(50) NOT NULL;

ALTER TABLE public.product
  RENAME COLUMN product_code TO product_id;